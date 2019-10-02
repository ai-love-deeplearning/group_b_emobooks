#!/usr/bin/env python
# coding: utf-8

# In[1]:


import json

dict_a = {"hoge" : 3, "fuga" : 2}
dict_b = {"hoge" : 5, "fuga" : 2}
dict_c = {"hoge" : 3, "fuga" : 2}

dict_list = [dict_a, dict_b, dict_c]
unique_list = list(map(json.loads, set(map(json.dumps, dict_list))))

f = open('ex.json', 'w')
json.dump(unique_list, f, indent=1)    


# In[ ]:




