from bs4 import BeautifulSoup
from urllib import request
from urllib.parse import quote_plus
from urllib.request import urlopen
import time
import codecs
import json
import random
from retrying import retry


def getBaseUlr(jsonfile):
  with open(jsonfile,'r',encoding="utf-8") as f:
    jsonData = json.load(f)
   
    ncode = []
    baseUlr = []
    Ulr = []
    for nobeldata in jsonData:
        if nobeldata.get("allcount") == None:
            ncode.append(nobeldata['ncode'])
            
    print(ncode)
    for i in ncode:
        # 作品本文ページのURL
        Ulr = 'https://ncode.syosetu.com/' + "{}/".format(i)
        baseUlr.append(Ulr)
        res = urlopen(Ulr)
        soup = BeautifulSoup(res, "html.parser")
        num_parts = len(soup.find_all(class_='subtitle'))
        #fileName = soup.find(class_='novel_title').text
        textFileName = i + '.txt'
        
        with open(textFileName, "w", encoding="utf-8") as f:
            for part in range(1, num_parts+1): 
            # 作品本文ページのURL
                url = Ulr + "{:d}/".format(part)
                res = request.urlopen(url)                  
                soup = BeautifulSoup(res, "html.parser")
                
                # CSSセレクタで本文を指定
                honbun = soup.select_one("#novel_honbun").text
                
                # 保存
                f.write(honbun)
                print("part {:d} downloaded".format(part))  # 進捗を表示
                time.sleep(1)  # 次の部分取得までは1秒間の時間を空
            else:
                f.close()
             

if __name__ == '__main__':
    getBaseUlr('../&order=dailypoint&genre=101.json')