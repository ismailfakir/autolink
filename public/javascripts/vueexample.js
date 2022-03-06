var app = new Vue({
        el: '#app',
        vuetify: new Vuetify(),
        props: {
            params: {
                type: Object,
                default: {name: 'name', price: 'price'}
            }
        },
        data: {
          valid: true,
          success: false,
          message: '',
          active: false,
          nameRules: [
                  v => !!v || 'Name is required',
                  v => (v && v.length <= 10) || 'Name must be less than 10 characters',
                ],
        },
        beforeMount: function () {
          this.params = JSON.parse(this.$el.attributes.params.value);
        },
        methods: {
              validate () {
                this.$refs.form.validate()
              },
              reset () {
                this.$refs.form.reset()
              },
              resetValidation () {
                this.$refs.form.resetValidation()
              },
          submit: function() {
            this.success = false;

            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = () => {
              if (xhr.readyState === 4) { // 4 means done
                let resp = JSON.parse(xhr.responseText);

                if (resp.success) {
                  this.message = 'Submitted successfully';
                } else {
                  this.message = 'A validation error occurs: ' + resp.messages.join(', ');
                }
              }
            };

            xhr.open('POST', '/connections');
            xhr.setRequestHeader('Content-Type', 'application/json;charset=UTF-8');
            xhr.send(JSON.stringify(this.params));
          }
        }
      })