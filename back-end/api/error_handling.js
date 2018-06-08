// Error object
let error_resp = {
  status : 'error',
  err_msg : 'An error has been encountered'
};

let handler = (error) => {

  // Update error message
  error_resp.err_msg = error;
  return error_resp;
  
}

module.exports = handler;
