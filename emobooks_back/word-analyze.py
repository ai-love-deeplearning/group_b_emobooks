import MeCab
import json
import numpy as np
from matplotlib import pyplot as plt
import warnings


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


def score_analyze(emo_dict, text_lines):
    i = 1
    count = 0
    score_list = []
    for line in text_lines:
        if line in emo_dict.keys():
            score_list.append(emo_dict[line])
            count += 1
        else:
            print(line + 'not in vocabrary')
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

    score_rength = [[0 for i in range(3)] for i in range(4)]
    score_rength[0][0] = len(score[0])
    score_rength[1][0] = len(score[1])
    score_rength[2][0] = len(score[2])
    score_rength[3][0] = len(score[3])

    j = 0
    while j < 4:
        score_rength[j][1] = int(score_rength[j][0] / 99)
        score_rength[j][2] = score_rength[j][0] - score_rength[j][1] * 99
        j += 1

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

    analyzed_array.append(add_array(score[0], percentage, last))
    analyzed_array.append(add_array(score[1], percentage, last))
    analyzed_array.append(add_array(score[2], percentage, last))
    analyzed_array.append(add_array(score[3], percentage, last))

    return analyzed_array


def main():
    with open('data.txt', 'r') as f:
        text = f.read()

    text = text_edit_main(text)
    data = create_emo_data_main(text)
    print(data)

    plt.plot(data[0], data[1], 'o-', label='happy')
    plt.plot(data[0], data[2], 'o-', label='angry')
    plt.plot(data[0], data[3], 'o-', label='sad')
    plt.plot(data[0], data[4], 'o-', label='fun')
    plt.legend(loc='best')
    plt.show()


main()
