<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Payment</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<script src="https://checkout.razorpay.com/v1/checkout.js"></script>
</head>
<body>
  <button onclick="paytmStart()">Pay</button>
<script>
const paytmStart = () => {
 

  fetch('/create_order', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ amount: 5, info: 'order_request' })
  })
  .then(response => response.json())
  .then(response => {
    console.log("AJAX request success"); 
    alert("success");
    alert("Order ID: " + response.id);
    alert(JSON.stringify(response));
    console.log(response);

    if (response.status === "created") {
      alert(response.amount);
      let options = {
        key: "rzp_test_igrEOuPAHcLSxg", // Replace with your Razorpay key
        amount: response.amount,
        currency: "INR",
        name: "Your Company Name",
        description: "Checkout Payment",
        image: "URL_TO_YOUR_COMPANY_LOGO",
        order_id: response.id,
        handler: function (response) {
          console.log(response.id);
          alert(response.id);
          console.log(response.razorpay_order_id);
          console.log(response.razorpay_signature);
          console.log("Payment success");
          alert("Payment success");
          window.location.href = "http://localhost:9000/myorders";
        },
        prefill: {
          name: "",
          email: "",
          contact: ""
        },
        notes: {
          address: "Razorpay Corporate Office"
        },
        theme: {
          color: "#3399cc"
        }
      };

      var rzp1 = new Razorpay(options);
      rzp1.on('payment.failed', function (response) {
        console.log(response.error.code);
        console.log(response.error.description);
        console.log(response.error.source);
        console.log(response.error.step);
        console.log(response.error.reason);
        console.log(response.error.metadata.order_id);
        console.log(response.error.metadata.payment_id);
      });

      rzp1.open();
      
    }
  })
  .catch(error => {
    console.log("Error message: " + error);
    alert("Error: " + error);
  });
};
</script>
</body>
</html> 