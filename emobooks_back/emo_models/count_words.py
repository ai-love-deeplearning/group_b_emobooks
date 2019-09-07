from gensim.models import word2vec

model = word2vec.Word2Vec.load('emo.model')
print('model1 is ' + str(model.corpus_count) + ' word.\n')
model_1 = word2vec.Word2Vec.load('emo_2.model')
print('model2 is ' + str(model_1.corpus_count) + ' word.\n')