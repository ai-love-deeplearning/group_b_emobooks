from gensim.models import word2vec

model = word2vec.Word2Vec.load("emo_2.model")

index2word = model.wv.index2word
file = open('model_list.txt', 'w')
for x in index2word:
    file.write(str(x) + "\n")
file.close()