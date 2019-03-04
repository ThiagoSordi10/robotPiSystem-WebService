var deleta_estacao = function(id){
	$.ajax({
	    url: '/api/estacao/' + id,
	    type: 'DELETE',
	    success: function(result) {
	    	location.reload();
	    }
	});
}