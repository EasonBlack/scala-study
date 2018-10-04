app.controller('Controller', ['$scope', '$http',
  function ($scope, $http) {

    $scope.items = [];
    $http.get('/api/brand').success( (result) => {
      $scope.items = result;
    })

    $scope.priceRule = (item)=>{
      alert(item.id)
    }

}])