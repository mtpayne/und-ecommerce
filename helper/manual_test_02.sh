
#
# Get user by id
echo "Create user. Check log for error message"
curl -H "Accept: application/json" -H "Content-Type: application/json" -d @helper/user_create_02.json http://localhost:8080/api/user/create
echo
echo
sleep 1

# All these should be forbidden
# Get user by id
echo "Get user by id"
curl -H "Content-Type: application/json" http://localhost:8080/api/user/id/1
echo
echo
sleep 1

# Get user by username
echo "Get user by username"
curl -H "Content-Type: application/json" http://localhost:8080/api/user/username_01
echo
echo
sleep 1

# List all items
echo "List all items"
curl -H "Content-Type: application/json" http://localhost:8080/api/item
echo
echo
sleep 1

# Get item by id
echo "Get item by id"
curl -H "Content-Type: application/json" http://localhost:8080/api/item/1
echo
echo
sleep 1

# Get item by name
echo "Get item by name"
curl -H "Content-Type: application/json" http://localhost:8080/api/item/name/Round%20Widget
echo
echo
sleep 1

# Add item to cart
echo "Adding item to cart"
curl -H "Content-Type: application/json" -d @helper/cart_add_01.json http://localhost:8080/api/cart/addToCart
echo
echo
sleep 1

# Remove item to cart
echo "Removing item to cart"
curl -H "Content-Type: application/json" -d @helper/cart_add_02.json http://localhost:8080/api/cart/removeFromCart
echo
echo
sleep 1

# Submit order
echo "Submit order"
curl -X POST -H "Content-Type: application/json" http://localhost:8080/api/order/submit/username_01
echo
echo
sleep 1

# Submit order
echo "View order history"
curl -H "Content-Type: application/json" http://localhost:8080/api/order/history/username_01
echo
echo


