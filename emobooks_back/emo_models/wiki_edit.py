file = open('wiki.txt', 'r')
editted_file = open('edit_wiki.txt', 'a')

line = file.readline()

i = 0
while line:
    if '<doc' in line:
        flg = True
    elif '</doc>' not in line:
        flg = False
    else:
        flg = True
    if not flg:
        editted_file.write(line)
    line = file.readline()
    i += 1
    if i % 100 == 0:
        print(str(i) + ' lines is editted.')
file.close()
editted_file.close()

print('finish.')
