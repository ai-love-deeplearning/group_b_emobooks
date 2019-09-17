with open('json_text.txt', 'r') as f:
    text = ''
    for i in f:
        text = text + i.replace('\n', '\\n')

with open('json_pre.txt', 'w') as f:
    f.write(text)