from gensim.models import word2vec

model = word2vec.Word2Vec.load("emo_2.model")
file = open('model_list.txt', 'r')
file_1 = open('dic_1.txt', 'a')
file_2 = open('dic_2.txt', 'a')
file_3 = open('dic_3.txt', 'a')
file_4 = open('dic_4.txt', 'a')
line = file.readline().rstrip('\n')

emotion = ['喜び', '怒り', '哀しみ', '楽しい']
i = 0

while line:
    file_1.write(line + ', ' + str(model.similarity(line, emotion[0])) + '\n')
    file_2.write(line + ', ' + str(model.similarity(line, emotion[1])) + '\n')
    file_3.write(line + ', ' + str(model.similarity(line, emotion[2])) + '\n')
    file_4.write(line + ', ' + str(model.similarity(line, emotion[3])) + '\n')
    line = file.readline().rstrip('\n')
    i += 1
    print('loop' + str(i))
file_4.close()
file_3.close()
file_2.close()
file_1.close()
file.close()
print('finish')
