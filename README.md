[![logo](https://github.com/hcvazquez/AIDT-ST-Retrieval/blob/master/img/customLogo.png?raw=true)](http://www.isistan.unicen.edu.ar/)

# ST-Rank
The rank component of AIDT



## Overview

ST-Rank proposes the application of automatic ranking techniques for the creation of a technology comparison model. In ST-Rank, the model is trained mainly based on technological decisions made by developers in popular open-source projects.

![logo](https://github.com/hcvazquez/AIDT-ST-Rank/blob/master/img/Enfoque-ST-Rank.png?raw=true)


### Installation

On the one hand, ST-Rank requires the Java Development Kit 1.8+ to run the data preparation.

Then, run the next task
```sh
run src/ranking/builder/TaskPipeline
```


On the other hand, ST-Rank requires the installation of python 3, jupyter notebook, sklearn and tensorflow, to execute the machine learning algorithms.

Examples of these algorithms can be executed as,
```sh
python notebook ./PairwiseRankMethods.ipynb
```

