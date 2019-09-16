import MeCab


def cut_text(edit_file):
    with open(edit_file, 'r') as f:
        memory_line = []
        for line in f:
            memory_line.append(line.strip())

    with open('pre-text.txt', 'w') as f:
        for i in memory_line:
            f.write(i)


def rewrite_with_wakati(memory_line, edit_file):
    with open(edit_file, 'r') as f:
        memory_line = f.readline()

    mecab = MeCab.Tagger("-Owakati")
    memory_line = mecab.parse(memory_line)

    with open('pre-text.txt', 'w') as f:
        f.write(memory_line)


def remove_text(memory_line, edit_file):
    with open(edit_file, 'r') as f:
        memory_line = f.readline()

    memory_line = memory_line.replace('「', '').replace(
        '」', '').replace('.', '').replace('・', '').replace('…', '')

    with open('pre-text.txt', 'w') as f:
        f.write(memory_line)


def separate_word(memory_line, edit_file):
    with open(edit_file, 'r') as f:
        memory_line = f.readline()
        text = memory_line.replace(' ', '\n')

    with open('pre-text.txt', 'w') as f:
        f.write(text)


def set_dictionary(emo_dict, dict_file, dict_line):
    while dict_line:
        item = dict_line.split(', ')[0]
        score = dict_line.split(', ')[1]
        emo_dict[item] = float(score)
        dict_line = dict_file.readline().rstrip('\n')


def score_analyze(emo_dict, score_list, f, not_file):
    i = 1
    count = 0
    for line in f:
        Line = line.rstrip('\n')
        if Line in emo_dict.keys():
            score_list.append(emo_dict[Line])
            count += 1
        else:
            print(Line + 'not in vocabrary')
            not_file.write(str(i) + ',' + Line + '\n')
            i += 1
    print(str(count) + ' word was analyzed. but ' +
          str(i - 1) + ' word is not found in vocabrary.\n')


def sum_value(score_analyze, score_rength, score_value):
    analyze = 1
    value = 0
    rength = 1
    while analyze < 101:
        count = 0
        while count < score_rength[rength]:
            score_analyze[analyze] += score_value[value]
            count += 1
            value += 1
        score_analyze[analyze] = score_analyze[analyze] / score_rength[rength]
        analyze += 1
        if analyze == 100:
            rength = 2


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


def text_edit_main():
    memory_line = ''
    edit_file = 'pre-text.txt'
    cut_text(edit_file)
    rewrite_with_wakati(memory_line, edit_file)
    remove_text(memory_line, edit_file)
    separate_word(memory_line, edit_file)
    with open('pre-text.txt', 'r', encoding='utf-8') as f:
        line = f.readlines()
        memory_list = []
        for i in line:
            if i != '\n':
                memory_list.append(i)

    with open('pre-text.txt', 'w', encoding='utf-8') as f:
        for r in memory_list:
            f.write(r)


def create_emo_data_main():
    file_1 = open('dic_happy.txt', 'r')
    file_2 = open('dic_angry.txt', 'r')
    file_3 = open('dic_sad.txt', 'r')
    file_4 = open('dic_fun.txt', 'r')

    line_1 = file_1.readline().rstrip('\n')
    line_2 = file_2.readline().rstrip('\n')
    line_3 = file_3.readline().rstrip('\n')
    line_4 = file_4.readline().rstrip('\n')

    list_count = 0
    with open('pre-text.txt') as f:
        for line in f:
            list_count += 1

    happy = {}
    angry = {}
    sad = {}
    fun = {}

    score_happy = []
    score_angry = []
    score_sad = []
    score_fun = []

    not_file = open('not_vocab_word.txt', 'a')

    set_dictionary(happy, file_1, line_1)
    set_dictionary(angry, file_2, line_2)
    set_dictionary(sad, file_3, line_3)
    set_dictionary(fun, file_4, line_4)

    with open('pre-text.txt', 'r') as f:
        score_analyze(happy, score_happy, f, not_file)
    with open('pre-text.txt', 'r') as f:
        score_analyze(angry, score_angry, f, not_file)
    with open('pre-text.txt', 'r') as f:
        score_analyze(sad, score_sad, f, not_file)
    with open('pre-text.txt', 'r') as f:
        score_analyze(fun, score_fun, f, not_file)

    file_1.close()
    file_2.close()
    file_3.close()
    file_4.close()

    not_file.close()

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

    analyzed_array = [[0 for i in range(101)] for i in range(4)]
    sum_value(analyzed_array[0], score_rength[0], score_happy)
    sum_value(analyzed_array[1], score_rength[1], score_angry)
    sum_value(analyzed_array[2], score_rength[2], score_sad)
    sum_value(analyzed_array[3], score_rength[3], score_fun)

    create_emotion_file(analyzed_array)


def main():
    text_edit_main()
    create_emo_data_main()


main()
