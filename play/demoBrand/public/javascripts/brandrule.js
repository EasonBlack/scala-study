app.controller('Controller', ['$scope', '$http',
  function ($scope, $http) {

    $scope.brands = []
    $scope.rules = []
    $scope.rule = {}
    
    $scope.search = () => {
      axios.get(`/api/ruleinfo?brandid=${$scope.rule.brandid}`)
      .then(resRules => {
        $scope.rules = resRules.data;
        $scope.$apply();
      })
    }

    $scope.addRule = () => {
      axios.post('/api/rule2' , {
        brandid: $scope.rule.brandid,
        name: $scope.rule.name,
        operator: $scope.rule.operator,
        num: $scope.rule.num
      }).then(res=>{
        $scope.search();
      });
    }

    $scope.delete = (item) => {
      axios.delete(`api/rule/${item.id}`)
      .then(res=>{
        $scope.search();
      })
    }

    $scope.setUpdate = (item) => {
      $scope.rule = {
        id: item.id,
        name: item.name,
        operator: item.operator,
        num: item.num,
        brandid: item.brandid
      }
    }

    $scope.updateRule = () => {
      axios.patch(`/api/rule/${$scope.rule.id}`, $scope.rule)
      .then(res=>{
        console.log(res);
        $scope.search();
      })
    }


    let init = ()=>{
      axios.get('/api/brand')
      .then(resultBrands => {
        $scope.brands = resultBrands.data;
        $scope.rule.brandid = $scope.brands[0].id;
        $scope.search();
      })
    }

    init()

}])