// JSON object to store the error message response
let error_resp = {
  // Default error message
  status : 'error',
  err_msg : 'An error has been encountered'
};

// Error Handler function to return a case-specific error message as JSON object
handler = (error) => {
  // Update the default error message
  error_resp.err_msg = error;
  return error_resp;
}

module.exports = handler;
