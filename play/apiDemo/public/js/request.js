app.controller('Controller', ['$scope', '$http', 
  function ($scope, $http) {

    $scope.model = {
      send1 : {
        input1: ''
      },
      send3:{
        id: 1,
        name: 'aaa'
      },
      send4: [
        {id: 0, name: ""},
        {id: 0, name: ""}
      ],
      send11:{
        input1: 'aaa',
        input2: 'bbb'
      },
      post2: [
        {name: ''},
        {name: ''}
      ]
    }
    
    $scope.send1 = () => {
      $http.get(`/api/req/send1?id=${$scope.model.send1.input1}`)
      .success(res=>{
        console.log(res);
      })
    }

    $scope.send11 = () => {
      $http.get(`/api/req/send11?id=${$scope.model.send11.input1}&name=${$scope.model.send11.input2}`)
      .success(res=>{
        console.log(res);
      })
    }

    $scope.send2 = () => {
      $http.get(`/api/req/send2/${$scope.model.send1.input1}`)
      .success(res=>{
        console.log(res);
      })
    }

    $scope.send3 = () => {
      axios.post(`/api/req/send3` , {
        id: parseInt($scope.model.send3.id),
        name:$scope.model.send3.name
      })
      .then(res=>{
        console.log(res);
      })
    }

    $scope.send4 = () => {
      axios.post(`/api/req/send4` , $scope.model.send4)
      .then(res=>{
        console.log(res);
      })
    }

    $scope.post1 = ()=> {
      axios.post(`/api/brand/post1`)
      .then(res=>{
        console.log(res);
      })
    }

    $scope.post2 = ()=> {
      axios.post(`/api/brand/post2` , $scope.model.post2)
      .then(res=>{
        console.log(res);
      })
    }

  }])