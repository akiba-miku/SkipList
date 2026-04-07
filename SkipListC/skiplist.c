/**
 * 以下代码是朴素跳表的面试风格实现
 * 不代表实际工程和C语言编写的风格。
 * 没有Redis版本的双关键字排序， 具体redis实现参照源码
 */

#include <stdio.h>
#include <stdlib.h>
#include <time.h>

#define MAX_LEVEL 32
#define P 0.25

typedef struct Node{
    int key;
    int value;
    struct Node *forward[MAX_LEVEL];
} Node;

typedef struct {
    int level;
    Node *head;
} SkipList;

Node *createNode(int key, int value) {
    Node *node = (Node*)malloc(sizeof(Node));
    node->key = key;
    node->value = value;
    for (int i = 0; i < MAX_LEVEL; i++) {
        node->forward[i] = NULL;
    }
    return node;
}

SkipList *createSkipList() {
    SkipList *list = (SkipList*)malloc(sizeof(SkipList));
    list->level = 1;
    list->head = createNode(-1, -1);
    return list;
}

int randomLevel() {
    int level = 1;
    while ((double)rand() / 0x80000000 < P && level < MAX_LEVEL) {
            ++level;
    }
    return level;
}


Node *search(SkipList *list, int key) {
    Node *cur = list->head;
    for(int i = list->level - 1; i >= 0; i--) {
        while (cur->forward[i] && cur->forward[i]->key < key) {
            cur = cur->forward[i];
        }
    }
    cur = cur->forward[0];
    if (cur && cur->key == key) {
        return cur;
    }
    return NULL;
}

void insert(SkipList *list, int key, int value) {
    Node *update[MAX_LEVEL];
    Node *cur = list->head;

    for (int i = list->level-1; i>=0; i--) {
        while(cur->forward[i] && cur->forward[i]->key < key) {
            cur = cur->forward[i];
        }
        update[i] = cur;
    }
    cur = cur->forward[0];

    if (cur && cur->key == key) {
        cur->value = value;
        return ;
    }

    int newLevel = randomLevel();
    if (newLevel > list->level) {
        for (int i = list->level;i < newLevel; i++) {
            update[i] = list->head;
        }
        list->level = newLevel;
    }

    Node *node = createNode(key, value);
    for (int i=0; i < newLevel; i++) {
        node->forward[i] = update[i]->forward[i];
        update[i]->forward[i] = node;
    }
}

void deleteNode(SkipList *list, int key) {
    Node *update[MAX_LEVEL];
    Node *cur = list->head;

    for (int i = list->level - 1; i >= 0; i--) {
        while(cur->forward[i] && cur->forward[i]->key < key) {
            cur = cur->forward[i];
        }
        update[i] = cur;
    }

    cur = cur->forward[0];
    if (cur == NULL || cur->key != key) {
        return;
    }

    for (int i=0;i<list->level;i++) {
        if (update[i]->forward[i] != cur) {
            break;
        }
        update[i]->forward[i] = cur->forward[i];
    }

    free(cur);
    while(list->level > 1 && list->head->forward[list->level-1] == NULL) {
        list->level--;
    }
}

void printList(SkipList *list) {
    Node *cur = list->head->forward[0];
    while (cur) {
        printf("(%d->%d) ", cur->key, cur->value);
        cur = cur->forward[0];
    }
    puts("");
}


int main() {
    srand((unsigned)time(NULL));

    SkipList *list = createSkipList();

    insert(list, 3, 30);
    insert(list, 1, 10);
    insert(list, 7, 70);
    insert(list, 5, 50);

    printList(list);

    Node *node = search(list, 5);
    if (node) {
        printf("found: key=%d value=%d\n", node->key, node->value);
    } else {
        printf("not found\n");
    }

    insert(list, 5, 500); // 更新
    printList(list);

    deleteNode(list, 3);
    printList(list);

    return 0;
}