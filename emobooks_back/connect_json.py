import json
import os

with open('yyyyyy.json', 'r') as f:  # 結合したいjsonファイルを指定（こっちが消える）
    connect_dict = json.load(f)

with open('yyyyy.json', 'r') as f:  # 結合先のjsonファイルを指定（こっちにくっつく）
    connect_dict.update(json.load(f))


with open('yyyyy.json', 'w') as f:
    json.dump(connect_dict, f, indent=4)

os.remove('yyyyyy.json')
