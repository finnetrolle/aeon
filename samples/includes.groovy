

def weather = WeatherAPIBuilder.build('http://api.openweathermap.org')

def map = ["id": "524901", "APPID": "cb48378eb0aa347364eabdcbb0fbb0d2"]

def result = weather.ask(map)

result
