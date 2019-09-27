import json
import os

with open('test1.json', 'r') as f:  # 結合したいjsonファイルを指定（こっちが消える）
    connect_dict = json.load(f)

with open('test2.json', 'r') as f:  # 結合先のjsonファイルを指定（こっちにくっつく）
    connect_list = json.load(f)
    for i in connect_list:
        connect_dict.append(i)

with open('test2.json', 'w') as f:
    json.dump(connect_dict, f, indent=4)

os.remove('test1.json')
