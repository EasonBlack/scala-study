app.controller('Controller', ['$scope', '$http',
  function ($scope, $http) {

    $scope.brands = [];
    $scope.category = {
    }
    $scope.items= []
    $scope.isEditDisplay = false;
    $scope.newCategorys = []

    $scope.search = ()=>{
      axios.get(`/api/category/${$scope.category.brandid}`)
      .then(res=>{
        $scope.items = res.data;
      })
    }
    $scope.cancel = ()=> {
      $scope.isEditDisplay = false;
      $scope.newCategorys = [];
    }

    $scope.addCategory = () => {
      $scope.newCategorys.push({name: '', brandid: $scope.category.brandid})
    }

    $scope.save = ()=>{
      axios.post('/api/category', $scope.newCategorys).then( (result) => {
        console.log(result);
        $scope.search();
      })
    }

    let init = ()=>{
      axios.get('/api/brand').then( (result) => {
        console.log(result);
        $scope.brands = result.data;
        $scope.$apply();
      })
    }

    init();

}])