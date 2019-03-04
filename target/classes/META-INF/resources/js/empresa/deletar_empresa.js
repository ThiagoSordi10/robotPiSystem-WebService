var deleta_user = function(id){
	$.ajax({
	    url: '/api/empresa/' + id,
	    type: 'DELETE',
	    success: function(result) {
	    	location.reload();
	    }
	});
}