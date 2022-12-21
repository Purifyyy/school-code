/*void sortIt(List* list, Node* firstN, Node* previousN, Node* currentN, Node* nextN, int* flag)
{
    Node* finder = firstN, * finderPrevious = firstN;
    while (true)
    {
        if (*flag == 1)
        {
            if (currentN->data > finder->data)
            {
                if (finder == previousN)
                {
                    currentN->next = finder;
                    finderPrevious->next = currentN;
                    finder->next = nextN;
                }
                else
                {
                    currentN->next = finder;
                    previousN->next = nextN;
                    if (finder == firstN)    //if (finderPrevious == firstN)
                    {
                        list->first = currentN;
                    }
                    else
                    {
                        finderPrevious->next = currentN;
                    }
                }
                break;
            }
        }
        else if (currentN->data > finder->data)
        {
            if (finder == previousN)
            {
                currentN->next = finder;
                finder->next = nextN;
                finderPrevious->next = currentN;
            }
            else
            {
                currentN->next = finder;
                previousN->next = nextN;
                if (finder == firstN)
                {
                    list->first = currentN;
                }
                else
                {
                    finderPrevious->next = currentN;
                }
            }
            break;
        }
        finderPrevious = finder;
        finder = finder->next;
    }
}

void insertionSort(List* list) {
    int flag = 0;
    Node* tmpNode = list->first, * previous = list->first;
    if (tmpNode == nullptr || tmpNode->next == nullptr)
    {
        return;
    }
    tmpNode = tmpNode->next;
    while (tmpNode)
    {
        if (tmpNode->next == nullptr)
        {
            if (tmpNode->data > previous->data)
            {
                flag = 1;
                sortIt(list, list->first, previous, tmpNode, nullptr, &flag);
            }
        }
        else if (tmpNode->data > previous->data)
        {
            if (previous == list->first)
            {
                previous->next = tmpNode->next;
                tmpNode->next = previous;
                list->first = tmpNode;
            }
            else
            {
                sortIt(list, list->first, previous, tmpNode, tmpNode->next, &flag);
            }
        }
        previous = tmpNode;
        tmpNode = tmpNode->next;
    }
}*/