import json
import requests
import gzip
import pprint
import codecs

# baseになるjsonでgzipに圧縮した形式のURL
# 今は各20個ずつ数は調整可
BASE_URL = "http://api.syosetu.com/novelapi/api/?out=json&gzip=5"

termSerecUrl = ['&order=hyoka', '&order=dailypoint', '&order=weeklypoint',
                '&order=monthlypoint', '&order=quarterpoint', '&order=yearlypoint']

genreSelection = ['&genre=101', '&genre=102', '&genre=201', '&genre=202', '&genre=301', '&genre=302', '&genre=303', '&genre=304', '&genre=305', '&genre=306',
                  '&genre=307', '&genre=401', '&genre=402', '&genre=403', '&genre=404', '&genre=9901', '&genre=9902', '&genre=9903', '&genre=9904', '&genre=9999', '&genre=9801']


def getApiData():

    for term in termSerecUrl:
        aipUrl = BASE_URL + term

        for genre in genreSelection:
            aipUrl = aipUrl + genre
            apiDataGzip = requests.get(aipUrl)
            apiDataGzip.encoding = 'gzip'
            apiDataGzip = apiDataGzip.content
            apiData = gzip.decompress(apiDataGzip).decode("utf-8")
            apiDataJson = json.loads(apiData)

            fileName = term + genre + '.json'

            fileWarite = codecs.open(fileName, 'w', 'utf-8')
            json.dump(apiDataJson, fileWarite, indent=4, ensure_ascii=False)


if __name__ == "__main__":
    getApiData()
