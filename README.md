# RecommenderSystems
**College project investigating Recommender Systems.**
**Based on a movie dataset from https://movielens.org/** 

**1. Non-Personalised**
**2. Personalised (Item Based Collaborative Filtering)**

Environment provided by lecturer. Following classes written by me:


## NP
**Similarity Metrics**
./RecommenderSystems/NP/src/alg/np/similarity/metric/RatingMetric.java
../RecommenderSystems/NP/src/alg/np/similarity/metric/IncConfidenceMetric.java
../NP/src/alg/np/similarity/metric/GenomeMetric.java

**Runnables**
./NP/src/alg/np/ExecuteNP_Expt.java


## ICBF
**Similarity Metrics**
./ICBF/src/similarity/metric/CosineMetric.java
./ICBF/src/similarity/metric/MeanSquaredDifferenceMetric.java
./ICBF/src/similarity/metric/PearsonMetric.java
./ICBF/src/similarity/metric/PearsonSigWeightingMetric.java

**Neighbourhoods**
./ICBF/src/alg/ib/neighbourhood/NearestNeighbourhood.java
./ICBF/src/alg/ib/neighbourhood/ThresholdNeighbourhood.java

**Predictors**
./ICBF/src/alg/ib/predictor/SimpleAveragePredictor.java
./ICBF/src/alg/ib/predictor/WeightedAveragePredictor.java
./ICBF/src/alg/ib/predictor/DeviationFromItemMeanPredictor.java

**Runnables**
./ICBF/src/alg/ib/ExecuteIB_Expt_1.java
./ICBF/src/alg/ib/ExecuteIB_Expt_2.java
./ICBF/src/alg/ib/ExecuteIB_Expt_3.java
