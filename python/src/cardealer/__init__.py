"""
Car Dealer Presentation Generator - Python Package

A package for generating editable PowerPoint presentations from vehicle listing data.
"""

__version__ = "1.0.0"
__author__ = "Car Dealer App"

from .models import DealerDetails, VehicleListing
from .presentation import PresentationBuilder
from .cli import main

__all__ = [
    "DealerDetails",
    "VehicleListing",
    "PresentationBuilder",
    "main",
]

