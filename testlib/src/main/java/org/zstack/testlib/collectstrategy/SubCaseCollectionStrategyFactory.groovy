package org.zstack.testlib.collectstrategy

/**
 * Created by lining on 2017/7/20.
 */
class SubCaseCollectionStrategyFactory {

    private static SubCaseCollectionStrategy defaultStrategy(){
        return new NearestSubCaseCollectionStrategy()
    }

    static SubCaseCollectionStrategy getSubCaseCollectionStrategy(String strategyName){
        if(strategyName == null){
            return defaultStrategy()
        } else if(NearestSubCaseCollectionStrategy.strategyName == strategyName){
            return NearestSubCaseCollectionStrategy()
        } else if(MostCompleteSubCaseCollectionStrategy.strategyName == strategyName){
            return new MostCompleteSubCaseCollectionStrategy()
        } else {
            assert false : "can not find SubCaseCollectionStrategy[${strategyName}]"
        }
    }
}
