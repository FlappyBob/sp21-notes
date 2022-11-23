#! https://zhuanlan.zhihu.com/p/579880533
# 2. 小丑setup合集

写了以防万一之后要用。反刍一下，懒得翻文档了。
[Lab 1: IntelliJ, Java, git](https://sp21.datastructur.es/materials/lab/lab1/lab1)

## git的设置和使用。
设置自己的用户名和用户邮箱。
``` java
 $ git config --global user.email "you@berkeley.edu" // 这个就是自己的邮箱。不用berkeley的。
 $ git config --global user.name "Your Name" // 用户名
```
github里创建新的repo之后，clone到本地, 在本地的folder会出现和repo一样名字的folder。
```
$ git clone https://github.com/FlappyBob/CS61B-sp21.git

```
加入cs61b的skeleton，也就是还没implement的文档
```
$ git remote add skeleton https://github.com/Berkeley-CS61B/skeleton-sp21.git
```

git里有一些比较有用的cmd可以用
```
git add <文件名>
git commit -m "修改的信息"
git branch -M main
git remote add origin https://github.com/FlappyBob/CS61B-sp21.git
git push -u origin main
```
目前来讲只要一个默认的branch就够了，git的知识之后再说，现在够用就行，之后不够再补充。
```
git remote add origin https://github.com/FlappyBob/CS61B-sp21.git
git branch -M main
git push -u origin main
```
加入他的作业skeleton网址。（确保自己已经在那个自己克隆的folder中了！）
```
 $ git remote add skeleton https://github.com/Berkeley-CS61B/skeleton-sp21.git

```
rename自己remote的branch的名字。(方便之后push用)
```
git remote add sp21 https://github.com/FlappyBob/CS61B-sp21.git
```

查询自己加入的remote的两个remotes，一个是自己的用于保存，一个是josh给的用来拿作业的raw文档。然后pull拉取其中的文档。
```
 $ git remote -v
$ git pull skeleton master
 ```
 

## IntelliJ的setup
安装过程记得勾选add bin to to path 那个键，暂时不知道有什么用加了再说。
**下plugin**：cs61b和java visualizer.、

小丑了，记得加jdk。
![](pic/1.png)

记得加proj的lib到所有的文件中，要不然之后一个一个加入很难受。
![](pic/2.png)

## Snap 
这个是用来backup所有文档用的。
josh是这么说的。
> Commits to your standard repository are made manually. Commits to the snaps repository are made automatically.

这个寄了因为我下的intellij太新了，然后snaps那个plugin太老搞不进去。

## 完成并且提交。
```
 git status // 用于查看目前的文档modified/ staged的情况。
git add <文件名>
git add lab1/* 可以add一个文件夹 （前提是在lab1的母dir中）
git commit -m "修改的信息"
```
文档提交 (搞完add commit之后)

```
git push <自己设的名字>
```
总的来说git就是用来retrieve之前所有的文件记录，版本控制方便coder使用和修改某个版本。remote存在的意义就是万一哪天电脑崩了可以在另一台电脑上从git服务器中重新搞到某个版本。add和commit自己想要储存的信息，方便之后检索之前的版本信息，然后push备份。




