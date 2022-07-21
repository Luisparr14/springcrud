let url = 'http://localhost:8080/api/listar';

function eliminar(id) {
  let url = 'http://localhost:8080/api/eliminar/' + id;
  fetch(url, {
    method: 'DELETE'
  }).then(response => response.json())
    .then(data => {
      if(data){
        alert('Eliminado');
        location.reload();
      }else{
        alert('Error al eliminar');
      }
    })
    .catch(error => console.log(error));    
}