tuchinoko
=========

Features
--------

TuchinokoはJavaクラスファイルの不正な解析を防止することを目的とします．
そのために，Javaクラスファイルを読みにくい形式に変換します．この変換を
難読化(obfuscation)と呼びます．主な機能は以下の通りです．

* 与えられたクラスファイル(Jarファイル含む)を指定された難読化手法で難読化します．
* 難読化手法はプラグイン形式で提供されます．
* 複数の難読化手法を順番に適用できます．

Requirements
------------

* 開発・実行環境
    * Java SE 6
* プロジェクト管理
    * [Maven 3.x](http://maven.apache.org/)
* コンパイルに必要．
    * [ASM 4.0](http://asm.objectweb.org)
    * [Apache Commons Cli 1.1](http://commons.apache.org/cli/)
    * [Talisman XmlCli 1.2.2](http://talisman.sourceforge.jp/xmlcli/)
* ユニットテストに必要．
    * [JUnit 4.10](http://www.junit.org/) for testing.

History of Tuchinoko
--------------------

Tuchinokoの多くの機能は，作者が過去に作成した難読化ツール
[DonQuixote](http://se.naist.jp/DonQuixote/)を基に開発されています．
DonQuixote は奈良先端科学技術大学院大学(以下，NAIST)が保有する特許を含
んでおり，上記ページで配布されています．このままDonQuixoteのバージョン
アップを続けられれば良いのですが，以下の理由により困難であると判断しま
した．そこで，DonQuixoteのバージョンアップは停止し，新規プロジェクトと
して，新たに難読化ツールを開発することに決めました．

* 作者はNAISTを卒業しており，DonQuixoteの新バージョンを公開するためのサー
  バアクセス手段を持っていない．そのため，新バージョンの公開には他人の
  手を借りなければならない．頻繁なバージョンアップを行うと，NAISTの担当
  者の負担となる．
* DonQuixoteは特許を保有するソフトウェアである．そのため，特許と自由な
  使用の両立が難しい．
* 同じ名前で自由に使えるソフトウェアと，制限のあるソフトウェアがあると
  混乱のもとになる．
* アーキテクチャを見直したい．

Authors
-------

Name:        玉田 春昭

Affiliation: 京都産業大学 コンピュータ理工学部

E-mail:      tamada[__at__]cc.kyoto-su.ac.jp

Web page:    https://github.com/tamada/tuchinoko/tree/

License
-------

Apache License Version 2.0

    Copyright 2012- Haruaki Tamada

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
