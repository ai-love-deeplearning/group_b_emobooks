from bs4 import BeautifulSoup
from urllib import request
from urllib.request import urlopen
import time
import json
import MeCab


def edit_text(text):
    text = text.replace('\n', '').replace(' ', '').replace('　', '')
    mecab = MeCab.Tagger("-Owakati")
    text = mecab.parse(text)
    text = text.replace('「', '').replace(
        '」', '').replace('.', '').replace('・', '').replace('…', '')
    text = text.replace(' ', '\n')
    return text


def set_dictionary(dict_file):
    emo_dict = {}
    with open(dict_file, 'r') as f:
        for data in f:
            item = data.split(', ')[0]
            score = data.split(', ')[1]
            emo_dict[item] = float(score)
    return emo_dict


def vocab_judge_and_list(word):
    with open('not_vocabrary.json', 'r') as f:
        data = json.load(f)
    if word in data.keys():
        data[word] += 1
    else:
        data[word] = 1
    with open('not_vocabrary.json', 'w') as f:
        json.dump(data, f, indent=4, ensure_ascii=False)


def score_analyze(emo_dict, text_lines):
    i = 1
    count = 0
    score_list = []
    for word in text_lines:
        if word in emo_dict.keys():
            score_list.append(emo_dict[word])
            count += 1
        else:
            vocab_judge_and_list(word)
            i += 1
    print(str(count) + ' word was analyzed. but ' +
          str(i - 1) + ' word is not found in vocabrary.\n')
    return score_list


def create_emotion_file(score_analyze):
    with open('emo_data.txt', 'a') as f:
        emo = 0
        while emo < 4:
            per = 0
            while per < 101:
                f.write(str(score_analyze[emo][per]))
                if per < 100:
                    f.write(',')
                per += 1
            if emo < 3:
                f.write('\n')
            emo += 1


def add_array(score_emo, percentage, last):
    cnt = 0
    data = []
    total = 0
    flg = 1
    position = percentage
    while flg < 11:
        while cnt < position:
            total += score_emo[cnt]
            cnt += 1
        data.append(total)
        position += percentage
        flg += 1
        total = 0

    return data


def create_input_data(data):
    edited_data = []
    i = 1
    while i < 5:
        text = ""
        for value in data[i]:
            text += str(value)
            text += ','
        edited_data.append(text.rstrip(','))
        i += 1
    return edited_data


def text_edit_main(text):
    text = edit_text(text)
    text_lines = text.split('\n')
    memory_list = []
    for i in text_lines:
        if i != '':
            memory_list.append(i)
    for i in memory_list:
        text += i
        text += '\n'
    text.rstrip('\n')
    return memory_list


def create_emo_data_main(text):
    file = []
    file.append('dic_happy.txt')
    file.append('dic_angry.txt')
    file.append('dic_sad.txt')
    file.append('dic_fun.txt')

    emo_dict_list = []
    score = []

    for i in file:
        emo_dict_list.append(set_dictionary(i))

    for dictionary in emo_dict_list:
        score.append(score_analyze(dictionary, text))

    analyzed_array = []
    percentage = int(len(score[0]) / 100)
    last = 100 - percentage * 99

    total_per = 10

    per_list = []
    per_list.append(total_per)
    for per in range(9):
        total_per += 10
        per_list.append(total_per)

    analyzed_array.append(per_list)

    for emo_score in score:
        analyzed_array.append(add_array(emo_score, percentage, last))

    return analyzed_array


def emotion_data(file_name):
    with open(file_name, 'r') as f:
        text = f.read()

    text = text_edit_main(text)
    data = create_emo_data_main(text)
    input_data = create_input_data(data)
    return input_data


def getBaseUlr(jsonfile):
    with open(jsonfile, 'r', encoding="utf-8") as f:
        jsonData = json.load(f)

    ncode = []
    baseUlr = []
    Ulr = []
    for nobeldata in jsonData:
        ncode.append(nobeldata['ncode'])

    print(ncode)
    cnt = 0
    for i in ncode:
        emotion_data_list = []
        # 作品本文ページのURL
        Ulr = 'https://ncode.syosetu.com/' + "{}/".format(i)
        baseUlr.append(Ulr)
        res = urlopen(Ulr)
        soup = BeautifulSoup(res, "html.parser")
        num_parts = len(soup.find_all(class_='subtitle'))
        if num_parts != jsonData[cnt]['num_of_part']:
            jsonData[cnt]['num_of_part'] = num_parts
        textFileName = i + '.txt'

        with open(textFileName, "w", encoding="utf-8") as f:
            for part in range(1, num_parts + 1):
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
        emotion_data_list = emotion_data(textFileName)
        for dictionary in jsonData:
            if i == dictionary['ncode']:
                dictionary['yorokobi'] = emotion_data_list[0]
                dictionary['ikari'] = emotion_data_list[1]
                dictionary['kanashimi'] = emotion_data_list[2]
                dictionary['tanoshimi'] = emotion_data_list[3]
        time.sleep(1)
    with open(jsonfile, 'w', encoding="utf-8") as f:
        json.dump(jsonData, f, indent=4, ensure_ascii=False)


if __name__ == '__main__':
    getBaseUlr('sample.json')  # ここだけを変更する
