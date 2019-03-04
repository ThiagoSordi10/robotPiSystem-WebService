var deleta_raspberry = function(id){
	$.ajax({
	    url: '/api/raspberry/' + id,
	    type: 'DELETE',
	    success: function(result) {
	    	location.reload();
	    }
	});
}