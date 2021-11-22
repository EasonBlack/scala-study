

// axios.post('/post-zip').then(res=>{
//   console.log(res)
// })

// fetch('/post-zip')
// .then(resp => resp.blob())

function str2bytes (str) {
  var bytes = new Uint8Array(str.length);
  for (var i=0; i<str.length; i++) {
     bytes[i] = str.charCodeAt(i);
   }
   return bytes;
}

// axios.post('/post-zip')

axios({
  method: "post",
  url: '/post-zip',
  responseType: "blob",
})
.then(res => {
    console.log(res)
    const content = res.data
    const blob = new Blob([content], {type:  'application/zip'})
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.style.display = 'none';
    a.href = url;
    // the filename you want
    a.download = 'todo-1.zip';
    document.body.appendChild(a);
    a.click();
    window.URL.revokeObjectURL(url);
    alert('your file has downloaded!'); // or you know, something with better UX...
  })
  .catch(() => alert('oh no!'));