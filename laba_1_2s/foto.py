import matplotlib.pyplot as plt
from matplotlib.sankey import Sankey

# Diagram for Java class TruckingCompany

fig, ax = plt.subplots(figsize=(6, 4))
ax.text(0.5, 0.9, 'TruckingCompany', horizontalalignment='center', fontsize=12, fontweight='bold', transform=ax.transAxes)

# Attributes
ax.text(0.2, 0.7, '- NameofCompany: String', fontsize=10, transform=ax.transAxes)
ax.text(0.2, 0.6, '- MoneyforMass: int', fontsize=10, transform=ax.transAxes)
ax.text(0.2, 0.5, '- TotalMass: int', fontsize=10, transform=ax.transAxes)

# Methods
ax.text(0.7, 0.7, '+ getMoneyforMass(): int', fontsize=10, transform=ax.transAxes)
ax.text(0.7, 0.6, '+ getTotalMass(): int', fontsize=10, transform=ax.transAxes)
ax.text(0.7, 0.5, '+ getNameofCompany(): String', fontsize=10, transform=ax.transAxes)
ax.text(0.7, 0.4, '+ get_revenue(): int', fontsize=10, transform=ax.transAxes)

# Constructor methods
ax.text(0.2, 0.3, '+ TruckingCompany(NameofCompany: String)', fontsize=10, transform=ax.transAxes)
ax.text(0.2, 0.2, '+ TruckingCompany(NameofCompany: String, MoneyforMass: int, TotalMass: int)', fontsize=10, transform=ax.transAxes)

ax.axis('off')
plt.show()
