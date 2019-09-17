import json


def main():
    with open('sample_test.json', encoding='utf-8') as f:
        df = json.load(f)
        df['n7105co']['data'] = [[0, 1], [2, 3]]
    with open('sample_test.json', 'w', encoding='utf-8') as f:
        json.dump(df, f, indent=4, ensure_ascii=False)


main()
