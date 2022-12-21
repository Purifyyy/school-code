#include <iostream>
#include <iomanip>
#include <cassert>
#include <list>
#include <map>
#include <queue>
#include <stack>
#include <set>
#include <vector>
#include <deque>
#include <algorithm>
#include <limits>

using namespace std;

class A {
public:
	void m1() { cout << "a1"; }
	virtual void m2() { cout << "a2"; }
};
class B : public A {
	void m1() { cout << "b1"; }
	void m2() { cout << "b2"; }
};
void f() {
	A* x = new B();
	x->m1();
	x->m2();
}struct EdgeTo;

// reprezentacia vrcholu grafu
struct Vertex {
    string name; // nazov vrcholu
    list<EdgeTo> edges; // zoznam susedov
};

// reprezentacia hrany grafu
struct EdgeTo {
    Vertex* endVertex; // koncovy vrchol
    unsigned length; // dlzka hrany
};

// reprezentacia grafu
struct Graph {
    list<Vertex> vertices;
};

// funkcia vrati adresu vrcholu (alebo nullptr, ak neexistuje) s nazvom 'name'
Vertex* getVertex(Graph* g, const string& name) {
    list<Vertex>::iterator it = find_if(g->vertices.begin(), g->vertices.end(), [&name](const Vertex& v) {
        return v.name == name;
    });
    if (it != g->vertices.end()) {
        return &(*it);
    }
    return nullptr;
}

// funkcia vrati adresu hrany (alebo nullptr, ak neexistuje) z vrcholu 'from' do vrcholu 'to'
EdgeTo* getEdge(Graph* g, const string& from, const string& to) {
    Vertex* v = getVertex(g, from);
    if (v) {
        list<EdgeTo>::iterator it = find_if(v->edges.begin(), v->edges.end(), [&to](const EdgeTo& e) {
            return e.endVertex->name == to;
        });
        if (it != v->edges.end()) {
            return &(*it);
        }
    }
    return nullptr;
}

// funkcia prida vrchol s nazvom 'name' do grafu
bool addVertex(Graph* g, const string& name) {
    if (getVertex(g, name)) {
        return false; // duplikat vrcholu do grafu nevlozime
    }
    g->vertices.push_back({ name, {} });
    return true;
}

// funkcia prida hranu do grafu (z vrcholu 'from' do vrcholu 'to' s dlzkou 'length')
bool addEdge(Graph* g, const string& from, const string& to, const unsigned length) {
    // hrana sa prida len, ak v grafe este neexistuje
    if (!getEdge(g, from, to)) {
        Vertex* v1 = getVertex(g, from);
        Vertex* v2 = getVertex(g, to);
        if (v1 && v2) {
            v1->edges.push_back({ v2, length });
            return true;
        }
    }
    return false; // duplikat hrany do grafu nevlozime
}

// funkcia na odstranenie vrcholu s nazvom 'name'
bool removeVertex(Graph* g, const string& name) {
    Vertex* v = getVertex(g, name);
    if (v) {
        // vrchol 'v' vymazeme zo vsetkych vystupnych hran
        for (Vertex& vtx : g->vertices) {
            vtx.edges.remove_if([v](const EdgeTo& e) { return e.endVertex == v; });
        }
        // vrchol 'v' vymazeme zo zoznamu vrcholov grafu
        g->vertices.remove_if([v](const Vertex& vtx) { return &vtx == v; });
        return true;
    }
    return false;
}

// funkcia na odstranenie hrany z vrcholu 'from' do vrcholu 'to'
bool removeEdge(Graph* g, const string& from, const string& to) {
    EdgeTo* e = getEdge(g, from, to);
    if (e) {
        Vertex* vfrom = getVertex(g, from);
        vfrom->edges.remove_if([&e](const EdgeTo& et) { return et.endVertex == e->endVertex; });
        return true;
    }
    return false;
}

// funkcia na ziskanie susedov zvoleneho vrcholu
vector<string> getNeighbors(Graph* g, const string& name) {
    vector<string> vec;
    Vertex* v = getVertex(g, name);
    if (v) {
        for (const EdgeTo& e : v->edges) {
            vec.push_back(e.endVertex->name);
        }
    }
    return vec;
}

// funkcia vrati vstupny stupen zvoleneho vrcholu
int inDegree(Graph* g, const string& name) {
    Vertex* v = getVertex(g, name);
    if (v) {
        int cnt{};
        for (const Vertex& vtx : g->vertices) {
            for (const EdgeTo& edg : vtx.edges) {
                if (edg.endVertex->name == name) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
    return -1;
}

// funkcia vrati vystupny stupen zvoleneho vrcholu
int outDegree(Graph* g, const string& name) {
    Vertex* v = getVertex(g, name);
    if (v) {
        return v->edges.size();
    }
    return -1;
}

// funkcia na prechod grafom do sirky zo zvoleneho vrcholu
void bfs(Graph* g, const string& name) {
    Vertex* v = getVertex(g, name);
    if (v) {
        queue<Vertex*> q;
        map<Vertex*, bool> visited;
        for_each(g->vertices.begin(), g->vertices.end(), [&visited](Vertex& p) {
            visited[&p] = false;
        });
        visited[v] = true;
        cout << v->name << " ";
        q.push(v);
        while (!q.empty()) {
            v = q.front();
            q.pop();
            for (const EdgeTo& e : v->edges) {
                if (!visited[e.endVertex]) {
                    visited[e.endVertex] = true;
                    cout << e.endVertex->name << " ";
                    q.push(e.endVertex);
                }
            }
        }
    }
}int main(){	f();	return 0;}