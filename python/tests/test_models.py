"""Tests for data models."""

import pytest
from cardealer.models import DealerDetails, VehicleListing
from cardealer.constants import FUEL_TYPES, GEARBOX_TYPES, ULEZ_CHOICES


def test_dealer_details_default():
    """Test default DealerDetails creation."""
    dealer = DealerDetails()
    assert dealer.name == "Your Dealership"
    assert dealer.phone == "0000 000 0000"
    assert dealer.email == "sales@example.com"
    assert dealer.website == "www.example.com"


def test_dealer_details_from_dict():
    """Test DealerDetails creation from dictionary."""
    data = {
        "name": "Test Dealer",
        "phone": "123-456-7890",
        "email": "test@example.com",
        "website": "www.test.com"
    }
    dealer = DealerDetails.from_dict(data)
    assert dealer.name == "Test Dealer"
    assert dealer.phone == "123-456-7890"


def test_vehicle_listing_from_dict():
    """Test VehicleListing creation from dictionary."""
    data = {
        "title": "Test Car",
        "price": "£10,000",
        "registration": "AB12 CDE",
        "year": "2020",
        "gearbox": "Automatic",
        "engine_size": "2.0 L",
        "fuel_type": "Petrol",
        "mileage": "10,000",
        "ulez": "Yes",
        "mot_expiry": "2025-01-01",
        "owners": "1"
    }
    vehicle = VehicleListing.from_dict(data)
    assert vehicle.title == "Test Car"
    assert vehicle.price == "£10,000"
    assert vehicle.gearbox == "Automatic"
    assert vehicle.fuel_type == "Petrol"


def test_vehicle_listing_invalid_gearbox():
    """Test that invalid gearbox raises ValueError."""
    data = {
        "title": "Test Car",
        "price": "£10,000",
        "registration": "AB12 CDE",
        "year": "2020",
        "gearbox": "Invalid",
        "engine_size": "2.0 L",
        "fuel_type": "Petrol",
        "mileage": "10,000",
        "ulez": "Yes",
        "mot_expiry": "2025-01-01"
    }
    with pytest.raises(ValueError, match="Gearbox must be one of"):
        VehicleListing.from_dict(data)

