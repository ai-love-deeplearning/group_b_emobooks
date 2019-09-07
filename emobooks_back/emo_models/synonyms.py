from gensim.models import word2vec
import pprint

model = word2vec.Word2Vec.load("emo_2.model")
results = model.wv.most_similar(positive=['体験'])
for result in results:
    print(result)

# pprint.pprint(model.similarity('怒り', '激怒'))#２単語間の単語の距離を出す
