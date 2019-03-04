var deleta_dispositivo = function(token){
	$.ajax({
	    url: '/api/dispositivo/' + token,
	    type: 'DELETE',
	    success: function(result) {
	    	location.reload();
	    }
	});
}