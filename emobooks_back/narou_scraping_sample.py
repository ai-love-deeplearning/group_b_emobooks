import time
from urllib import request
from bs4 import BeautifulSoup

# Re:ゼロ n2267be 1~478
# 大英雄: n1170bs 1~86, 90 ~ 120

num_parts = 27  # ここに作品の全部分数を指定

with open("大英雄.txt", "w", encoding="utf-8") as f:
    for part in range(1, num_parts+1):
        # 作品本文ページのURL
        url = "https://ncode.syosetu.com/n1170bs/{:d}/".format(part)

        res = request.urlopen(url)
        soup = BeautifulSoup(res, "html.parser")

        # CSSセレクタで本文を指定
        honbun = soup.select_one("#novel_honbun").text

        # Arcadia用
        # honbun = soup.find("div", style="line-height:1.5").text
        honbun += "\n"  # 次の部分との間は念のため改行しておく

        # 保存
        f.write(honbun)

        print("part {:d} downloaded".format(part))  # 進捗を表示

        time.sleep(1)  # 次の部分取得までは1秒間の時間を空ける


