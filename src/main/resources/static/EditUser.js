$(async function () {
    editUser();

});

function editUser() {
    const editForm = document.forms["formEditUser"];
    editForm.addEventListener("submit", ev => {
        ev.preventDefault();
        let editUserRoles = [];
        for (let i = 0; i < editForm.roles.options.length; i++) {
            if (editForm.roles.options[i].selected) editUserRoles.push({
                id: editForm.roles.options[i].value,
                name: editForm.roles.options[i].name
            })
        }

        let promise = fetch("http://localhost:8080/api/admin/update/" + editForm.id.value , {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: editForm.id.value,
                username: editForm.username.value,
                lastName: editForm.lastName.value,
                email: editForm.email.value,
                password: editForm.password.value,
                roles: editUserRoles
            })
        });
        promise.then(() => {
            $('#editFormCloseButton').click();
            allUsers();
        })
    })
}