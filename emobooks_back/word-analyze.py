def set_dictionary(emo_dict, dict_file, dict_line):
    while dict_line:
        item = dict_line.split(', ')[0]
        score = dict_line.split(', ')[1]
        emo_dict[item] = float(score)
        dict_line = dict_file.readline().rstrip('\n')


def score_analyze(emo_dict, score_list, list_file, list_line, not_file):
    i = 1
    count = 0
    while list_line:
        if list_line in emo_dict.keys():
            score_list.append(emo_dict[list_line])
            count += 1
        else:
            print(list_line + 'not in vocabrary')
            not_file.write(str(i) + ',' + list_line + '\n')
            i += 1
        list_line = list_file.readline().rstrip('\n')
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
                f.write(score_analyze[emo][per])
                if per < 100:
                    f.write(',')
                per += 1
            f.write('\n')
            emo += 1


def main():
    file_1 = open('dic_1.txt', 'r')
    file_2 = open('dic_2.txt', 'r')
    file_3 = open('dic_3.txt', 'r')
    file_4 = open('dic_4.txt', 'r')

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

    list_file = open('pre-text.txt', 'r')
    list_line = list_file.readline().rstrip('\n')

    not_file = open('not_vocab_word.txt', 'a')

    set_dictionary(happy, file_1, line_1)
    set_dictionary(angry, file_2, line_2)
    set_dictionary(sad, file_3, line_3)
    set_dictionary(fun, file_4, line_4)

    score_analyze(happy, score_happy, list_file, list_line, not_file)
    score_analyze(angry, score_angry, list_file, list_line, not_file)
    score_analyze(sad, score_sad, list_file, list_line, not_file)
    score_analyze(fun, score_fun, list_file, list_line, not_file)

    file_1.close()
    file_2.close()
    file_3.close()
    file_4.close()

    not_file.close()

    score_rength = [[0 for i in range(4)] for i in range(3)]
    for i in score_happy:
        score_rength[0][0] += i
    for i in score_angry:
        score_rength[1][0] += i
    for i in score_sad:
        score_rength[2][0] += i
    for i in score_fun:
        score_rength[3][0] += i

    j = 0
    while j < 4:
        score_rength[j][1] = int(score_rength[j][0] / 99)
        score_rength[j][2] = score_rength[j][0] - score_rength[j][1] * 99
        j += 1

    analyzed_array = [[0 for i in range(4)] for i in range(101)]
    sum_value(analyzed_array[0], score_rength[0], score_happy)
    sum_value(analyzed_array[1], score_rength[1], score_angry)
    sum_value(analyzed_array[2], score_rength[2], score_sad)
    sum_value(analyzed_array[3], score_rength[3], score_fun)

    create_emotion_file(analyzed_array)


main()
