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


def main():
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


main()
