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


file_1 = open('dic_1.txt', 'r')
file_2 = open('dic_2.txt', 'r')
file_3 = open('dic_3.txt', 'r')
file_4 = open('dic_4.txt', 'r')

line_1 = file_1.readline().rstrip('\n')
line_2 = file_2.readline().rstrip('\n')
line_3 = file_3.readline().rstrip('\n')
line_4 = file_4.readline().rstrip('\n')

list_count = 0
with open('word_list_completed.txt') as f:
    for line in f:
        list_count += 1

like = {}
Anger = {}
sorrow = {}
fun = {}

score_like = []
score_Anger = []
score_sorrow = []
score_fun = []

list_file = open('pre3.txt', 'r')
list_line = list_file.readline().rstrip('\n')

not_file = open('not_vocab_word.txt', 'a')

set_dictionary(like, file_1, line_1)
set_dictionary(Anger, file_2, line_2)
set_dictionary(sorrow, file_3, line_3)
set_dictionary(fun, file_4, line_4)

score_analyze(like, score_like, list_file, list_line, not_file)


file_1.close()
file_2.close()
file_3.close()
file_4.close()
