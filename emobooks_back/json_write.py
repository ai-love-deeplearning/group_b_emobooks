import json


def main():
    with open('sample_test.json') as f:
        df = json.load(f)

    print(df['n7105co'])
main()
