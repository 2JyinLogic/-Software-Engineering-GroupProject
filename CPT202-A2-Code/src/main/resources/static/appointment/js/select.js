  (function() {
    let selectedService = null;
    function selectService(event) {
      if (selectedService) {
        selectedService.classList.remove('selected');
      }

      selectedService = event.currentTarget;
      selectedService.classList.add('selected');

      const radio = selectedService.querySelector('input[type="radio"]');
      radio.checked = true;
    }

    const serviceOptions = document.querySelectorAll('.service-select .option');
    serviceOptions.forEach(serviceOption => {
      serviceOption.addEventListener('click', selectService);
      serviceOption.addEventListener('mousedown', (event) => {
        event.preventDefault();
      });
    });
  })();



  (function() {
    function selectGroomer(event) {
      const previousSelected = document.querySelector('.block.selected');
      if (previousSelected) {
        previousSelected.classList.remove('selected');
      }

      const selectedGroomer = event.currentTarget;
      selectedGroomer.classList.add('selected');

      const radio = selectedGroomer.querySelector('input[type="radio"]');
      radio.checked = true;
    }

    const groomerOptions = document.querySelectorAll('.block');
    groomerOptions.forEach(groomerOption => {
      groomerOption.addEventListener('click', selectGroomer);
      groomerOption.addEventListener('mousedown', (event) => {
        event.preventDefault();
      });
    });
  })();



  (function() {
    let previousSelected = null;
    let previousChecked = null;
    function selectsubService(event) {
      const selectedService = event.currentTarget;
      const selectedRadio = selectedService.querySelector('input[type="radio"]');

      if (selectedRadio === previousChecked) {
        selectedRadio.checked = false;
        previousChecked = null;

        if (previousSelected) {
          previousSelected.classList.remove('selected');
        }
        previousSelected = null;
      } else {
        selectedRadio.checked = true;
        previousChecked = selectedRadio;

        if (previousSelected) {
          previousSelected.classList.remove('selected');
        }
        selectedService.classList.add('selected');
        previousSelected = selectedService;
      }
    }



    const serviceElements = document.querySelectorAll('.sup-option');
    serviceElements.forEach(option => {
      option.addEventListener('click', selectsubService);
    });
  })();
