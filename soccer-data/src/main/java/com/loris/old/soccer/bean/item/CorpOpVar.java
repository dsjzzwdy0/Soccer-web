package com.loris.old.soccer.bean.item;

import com.loris.common.util.NumberUtil;

public class CorpOpVar
{
	protected int num;
	protected float[] vars = new float[12];
	
	public CorpOpVar()
	{
	}
	
	public CorpOpVar(int num, float vars[])
	{
		if(vars.length != 12)
		{
			throw new IllegalArgumentException("The vars length is not 12, error.");
		}
		this.num = num;
		this.vars = vars;
	}
	public int getNum()
	{
		return num;
	}
	public void setNum(int num)
	{
		this.num = num;
	}
	
	public float[] getVars()
	{
		return vars;
	}
	public void setVars(float[] vars)
	{
		this.vars = vars;
	}

	public float getFirstwindiff()
	{
		return vars[0];
	}

	public void setFirstwindiff(float firstwindiff)
	{
		this.vars[0] = firstwindiff;
	}
	public float getFirstdrawdiff()
	{
		return vars[1];
	}

	public void setFirstdrawdiff(float firstdrawdiff)
	{
		this.vars[1] = firstdrawdiff;
	}

	public float getFirstlosediff()
	{
		return vars[2];
	}

	public void setFirstlosediff(float firstlosediff)
	{
		this.vars[2] = firstlosediff;
	}

	public float getFirstwinstd()
	{
		return vars[3];
	}

	public void setFirstwinstd(float firstwinstd)
	{
		this.vars[3] = firstwinstd;
	}

	public float getFirstdrawstd()
	{
		return vars[4];
	}

	public void setFirstdrawstd(float firstdrawstd)
	{
		this.vars[4] = firstdrawstd;
	}

	public float getFirstlosestd()
	{
		return vars[5];
	}

	public void setFirstlosestd(float firstlosestd)
	{
		this.vars[5] = firstlosestd;
	}

	public float getWindiff()
	{
		return vars[6];
	}

	public void setWindiff(float windiff)
	{
		this.vars[6] = windiff;
	}

	public float getDrawdiff()
	{
		return vars[7];
	}

	public void setDrawdiff(float drawdiff)
	{
		this.vars[7] = drawdiff;
	}

	public float getLosediff()
	{
		return vars[8];
	}

	public void setLosediff(float losediff)
	{
		this.vars[8] = losediff;
	}

	public float getWinstd()
	{
		return vars[9];
	}

	public void setWinstd(float winstd)
	{
		this.vars[9] = winstd;
	}

	public float getDrawstd()
	{
		return vars[10];
	}

	public void setDrawstd(float drawstd)
	{
		this.vars[10] = drawstd;
	}

	public float getLosestd()
	{
		return vars[11];
	}

	public void setLosestd(float losestd)
	{
		this.vars[11] = losestd;
	}
	
	@Override
	public String toString()
	{
		int max = vars.length - 1;
		StringBuffer sb = new StringBuffer();
		sb.append("OpVar [" + num + ", ");
		for (int i = 0; i < max; i ++)
		{
			sb.append(NumberUtil.formatDouble(2, vars[i]) + ", ");
		}
		sb.append(NumberUtil.formatDouble(2, vars[max]) + "]");
		return sb.toString();
	}
}
