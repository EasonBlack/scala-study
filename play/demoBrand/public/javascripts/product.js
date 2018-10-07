app.controller('Controller', ['$scope', '$http',
  function ($scope, $http) {

    $scope.brands = [];
    $scope.selectBrand = null;
    $scope.categorys = [];
    $scope.selectCategory = null;
    $scope.items= []
    $scope.isEditDisplay = false;
    $scope.newProducts = []

    $scope.brandChange = () => {
      axios.get(`/api/category/${$scope.selectBrand}`)
      .then(res=>{
        console.log(res);
        $scope.categorys = res.data;
        $scope.$apply();
      })
    }

    $scope.search = ()=>{
      axios.get(`/api/productInfo/${$scope.selectCategory}`)
      .then(res=>{
        $scope.items = res.data;
        $scope.$apply();
      })
    }
    $scope.cancel = ()=> {
      $scope.isEditDisplay = false;
      $scope.newProducts = [];
    }

    $scope.addProduct = () => {
      $scope.newProducts.push({name: '', categoryid: $scope.selectCategory})
    }

    $scope.save = ()=>{
      axios.post('/api/product', $scope.newProducts).then( (result) => {
        console.log(result);
        $scope.search();
      })
    }

    let init = ()=>{
      axios.get('/api/brand').then( (result) => {
        $scope.brands = result.data;
        $scope.$apply();
      })
    }

    init();

}])