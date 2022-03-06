/*jsRoutes.controllers.JsRoutesController.getBackendMessage().ajax({
        success: function(result) {
            alert("Message: " + result);
        },
        failure: function(err) {
            alert('There was an error');
        }
});*/

/* delete user modal*/
$('#delete-user-modal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('id') // Extract info from data-* attributes
    var name = button.data('name') // Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this)
    modal.find('.modal-body').text('Are you sure to delete user: ' + name)
    modal.find('#deleteuser-id').attr('href','/deleteuser/' + id)
});

/* edit user modal*/

$('#edit-user-modal').on('show.bs.modal', function (event) {
    var button = $(event.relatedTarget) // Button that triggered the modal
    var id = button.data('id') // Extract info from data-* attributes
    var name = button.data('name') // Extract info from data-* attributes
    var email = button.data('email') // Extract info from data-* attributes
    var password = button.data('password') // Extract info from data-* attributes
    var role = button.data('role') // Extract info from data-* attributes
    // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
    // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
    var modal = $(this)
    modal.find('#txt-user-id-edit').val(id)
    modal.find('#txt-user-name-edit').val(name)
    modal.find('#txt-user-email-edit').val(email)
    modal.find('#txt-user-password-edit').val(password)
    modal.find('#select-user-role-edit').val(role)
    //modal.find('#edituser-id').attr('href','/edituser/' + id)
});
