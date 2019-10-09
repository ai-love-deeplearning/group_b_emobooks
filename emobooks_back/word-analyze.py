import MeCab
import json


def edit_text(text):
    text = text.replace('\n', '').replace(' ', '').replace('　', '')
    mecab = MeCab.Tagger("-Owakati")
    text = mecab.parse(text)
    text = text.replace('「', '').replace(
        '」', '').replace('.', '').replace('・', '').replace('…', '')
    text = text.replace(' ', '\n')
    return text


def set_dictionary(emo_dict, dict_file, dict_line):
    while dict_line:
        item = dict_line.split(', ')[0]
        score = dict_line.split(', ')[1]
        emo_dict[item] = float(score)
        dict_line = dict_file.readline().rstrip('\n')


def score_analyze(emo_dict, score_list, text_lines):
    i = 1
    count = 0
    for line in text_lines:
        if line in emo_dict.keys():
            score_list.append(emo_dict[line])
            count += 1
        else:
            print(line + 'not in vocabrary')
            i += 1
    print(str(count) + ' word was analyzed. but ' +
          str(i - 1) + ' word is not found in vocabrary.\n')


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


def add_array(analyzed_array, score_emo):
    i = 0


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
    file_1 = open('dic_happy.txt', 'r')
    file_2 = open('dic_angry.txt', 'r')
    file_3 = open('dic_sad.txt', 'r')
    file_4 = open('dic_fun.txt', 'r')

    line_1 = file_1.readline().rstrip('\n')
    line_2 = file_2.readline().rstrip('\n')
    line_3 = file_3.readline().rstrip('\n')
    line_4 = file_4.readline().rstrip('\n')

    happy = {}
    angry = {}
    sad = {}
    fun = {}

    score_happy = []
    score_angry = []
    score_sad = []
    score_fun = []

    set_dictionary(happy, file_1, line_1)
    set_dictionary(angry, file_2, line_2)
    set_dictionary(sad, file_3, line_3)
    set_dictionary(fun, file_4, line_4)

    score_analyze(happy, score_happy, text)
    score_analyze(angry, score_angry, text)
    score_analyze(sad, score_sad, text)
    score_analyze(fun, score_fun, text)

    file_1.close()
    file_2.close()
    file_3.close()
    file_4.close()

    score_rength = [[0 for i in range(3)] for i in range(4)]
    score_rength[0][0] = len(score_happy)
    score_rength[1][0] = len(score_angry)
    score_rength[2][0] = len(score_sad)
    score_rength[3][0] = len(score_fun)

    j = 0
    while j < 4:
        score_rength[j][1] = int(score_rength[j][0] / 99)
        score_rength[j][2] = score_rength[j][0] - score_rength[j][1] * 99
        j += 1

    analyzed_array = [[0 for i in range(len(text) + 1)] for i in range(5)]
    percentage = 100 / len(text)
    total_per = 0
    for per in analyzed_array[0]:
        per = total_per
        total_per += percentage
    add_array(analyzed_array, score_happy)
    return analyzed_array


def main():
    with open('data.txt', 'r') as f:
        text = f.read()

    text = text_edit_main(text)
    print((100 / len(text)) * len(text))
    #data = create_emo_data_main(text)


main()
