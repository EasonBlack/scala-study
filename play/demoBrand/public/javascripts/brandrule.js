app.controller('Controller', ['$scope', '$http',
  function ($scope, $http) {

    $scope.brands = []
    $scope.rules = []
    $scope.rule = {}


    
    $scope.search = () => {
      axios.get(`/api/rule?brandid=${$scope.rule.brandid}`)
      .then(resRules => {
        $scope.rules = resRules.data;
        $scope.$apply();
      })
    }



    $scope.addRule = () => {

      axios.post('/api/rule' , {
        brandid: $scope.rule.brandid,
        name: $scope.rule.name,
        operator: $scope.rule.operator,
        num: $scope.rule.num
      }).then(res=>{
        $scope.search();
      });
      // $http({
      //   method: 'POST',
      //   url: '/api/rule',
      //   data: {
      //     brandid: $scope.rule.brandid,
      //     name: $scope.rule.name,
      //     operator: $scope.rule.operator,
      //     num: $scope.rule.num
      //   }
      // }).success(result => {
      //   console.log(result)
      // }) 
    }

    let init = ()=>{
      axios.get('/api/brand')
      .then(resultBrands => {
        $scope.brands = resultBrands.data;
        $scope.rule.brandid = $scope.brands[0].id;
        $scope.search();
        // axios.get(`/api/rule?brandid=${$scope.rule.brandid}`)
        // .then(resultRules => {
        //   $scope.rules = resultRules.data;
        //   $scope.$apply();
        // })
      })
    }

    init()

}])