from flask import Flask, request

import numpy as np
import pickle
from sklearn.preprocessing import OneHotEncoder
from sklearn.compose import ColumnTransformer
from sklearn.preprocessing import StandardScaler

app = Flask(__name__)

clf_crop = pickle.load(open("clf_crop.pkl", 'rb'))
preprocesser = pickle.load(open("preprocesser.pkl", 'rb'))
clf_profit=pickle.load(open("clf_profit.pkl",'rb'))

@app.route("/", methods=['GET', 'POST'])
def index():
    crop_dict={
    1:'Rice',
    2:'Wheat',
    3:'Cotton',
    4:'Tea',
    5:'Soybean',
    6:'Barley',
    7:'Maize',
    8:'Coconut',
    9:'Apple',
    10:'Millets',
    11:'Sugarcane',
    12:'Groundnut',
    13:'Jute' ,
    14:'Potato',
    15:'Corn',
    16:'Mango',
    17:'Cashew' ,
    18:'Orange',
    19:'Grapes',
    20:'Banana',
    21:'Pineapple',
    22:'Rubber',
    23:'Strawberry',
    24:'Onion',
    25:'Pepper',
    26:'Chickpeas'
    }
    
    if request.method == 'POST':
        location = request.form['location']
        soil_type = request.form['soil_type']
        rainfall = int(request.form['rainfall'])
        area = int(request.form['area'])
        investment = int(request.form['investment'])
        print("Received data from Android app:")
        print("Location:", location)
        print("Soil Type:", soil_type)
        print("Rainfall:", rainfall)
        print("Area:", area)
        print("Investment:", investment)

        def recommendation(Location, Soil_Type, Rainfall, Area_Cultivated, Investment):
            features = np.array([[Location, Soil_Type, Rainfall, Area_Cultivated, Investment]])
            transformed_features = preprocesser.transform(features)

            prediction_crop = clf_crop.predict(transformed_features).reshape(1, -1)
            prediction_profit = clf_profit.predict(transformed_features).reshape(1, -1)

            return prediction_crop, prediction_profit

        predict_crop, predict_profit = recommendation(location, soil_type, rainfall, area, investment)

        if predict_crop[0][0] in crop_dict:
            crop = crop_dict[predict_crop[0][0]]
            profit = str(predict_profit[0][0])
            response = f"{crop} {profit}"
        else:
            response = "Sorry, we are not able to recommend a proper crop for this environment."
            
        return response

    return """
    <form method="post">
        Location: <input type="text" name="location"><br>
        Soil Type: <input type="text" name="soil_type"><br>
        Rainfall: <input type="number" name="rainfall"><br>
        Area Cultivated: <input type="number" name="area"><br>
        Investment: <input type="number" name="investment"><br>
        <input type="submit" value="Submit">
    </form>
    """

if __name__ == '__main__':
    app.run(port=8080, debug=True)
